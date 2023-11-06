import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('MarketSegmentationTypeB2bCategories e2e test', () => {
  const marketSegmentationTypeB2bCategoriesPageUrl = '/market-segmentation-type-b-2-b-categories';
  const marketSegmentationTypeB2bCategoriesPageUrlPattern = new RegExp('/market-segmentation-type-b-2-b-categories(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const marketSegmentationTypeB2bCategoriesSample = {
    value: 'Handmade circuit',
    uniqueCharacteristic: 'Buckinghamshire Internal',
    language: 'ENGLISH',
  };

  let marketSegmentationTypeB2bCategories;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/market-segmentation-type-b-2-b-categories+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/market-segmentation-type-b-2-b-categories').as('postEntityRequest');
    cy.intercept('DELETE', '/api/market-segmentation-type-b-2-b-categories/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (marketSegmentationTypeB2bCategories) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/market-segmentation-type-b-2-b-categories/${marketSegmentationTypeB2bCategories.id}`,
      }).then(() => {
        marketSegmentationTypeB2bCategories = undefined;
      });
    }
  });

  it('MarketSegmentationTypeB2bCategories menu should load MarketSegmentationTypeB2bCategories page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('market-segmentation-type-b-2-b-categories');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('MarketSegmentationTypeB2bCategories').should('exist');
    cy.url().should('match', marketSegmentationTypeB2bCategoriesPageUrlPattern);
  });

  describe('MarketSegmentationTypeB2bCategories page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(marketSegmentationTypeB2bCategoriesPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create MarketSegmentationTypeB2bCategories page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/market-segmentation-type-b-2-b-categories/new$'));
        cy.getEntityCreateUpdateHeading('MarketSegmentationTypeB2bCategories');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', marketSegmentationTypeB2bCategoriesPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/market-segmentation-type-b-2-b-categories',
          body: marketSegmentationTypeB2bCategoriesSample,
        }).then(({ body }) => {
          marketSegmentationTypeB2bCategories = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/market-segmentation-type-b-2-b-categories+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/market-segmentation-type-b-2-b-categories?page=0&size=20>; rel="last",<http://localhost/api/market-segmentation-type-b-2-b-categories?page=0&size=20>; rel="first"',
              },
              body: [marketSegmentationTypeB2bCategories],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(marketSegmentationTypeB2bCategoriesPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details MarketSegmentationTypeB2bCategories page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('marketSegmentationTypeB2bCategories');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', marketSegmentationTypeB2bCategoriesPageUrlPattern);
      });

      it('edit button click should load edit MarketSegmentationTypeB2bCategories page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('MarketSegmentationTypeB2bCategories');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', marketSegmentationTypeB2bCategoriesPageUrlPattern);
      });

      it('edit button click should load edit MarketSegmentationTypeB2bCategories page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('MarketSegmentationTypeB2bCategories');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', marketSegmentationTypeB2bCategoriesPageUrlPattern);
      });

      it('last delete button click should delete instance of MarketSegmentationTypeB2bCategories', () => {
        cy.intercept('GET', '/api/market-segmentation-type-b-2-b-categories/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('marketSegmentationTypeB2bCategories').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', marketSegmentationTypeB2bCategoriesPageUrlPattern);

        marketSegmentationTypeB2bCategories = undefined;
      });
    });
  });

  describe('new MarketSegmentationTypeB2bCategories page', () => {
    beforeEach(() => {
      cy.visit(`${marketSegmentationTypeB2bCategoriesPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('MarketSegmentationTypeB2bCategories');
    });

    it('should create an instance of MarketSegmentationTypeB2bCategories', () => {
      cy.get(`[data-cy="value"]`).type('wireless').should('have.value', 'wireless');

      cy.get(`[data-cy="description"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="placeholder"]`).type('invoice algorithm').should('have.value', 'invoice algorithm');

      cy.get(`[data-cy="uniqueCharacteristic"]`).type('open Group').should('have.value', 'open Group');

      cy.get(`[data-cy="language"]`).select('GREEK');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        marketSegmentationTypeB2bCategories = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', marketSegmentationTypeB2bCategoriesPageUrlPattern);
    });
  });
});
