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

describe('MarketSegmentationTypeB2cCategories e2e test', () => {
  const marketSegmentationTypeB2cCategoriesPageUrl = '/market-segmentation-type-b-2-c-categories';
  const marketSegmentationTypeB2cCategoriesPageUrlPattern = new RegExp('/market-segmentation-type-b-2-c-categories(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const marketSegmentationTypeB2cCategoriesSample = {
    value: 'platforms quantify',
    uniqueCharacteristic: 'Meadows Games',
    language: 'GREEK',
  };

  let marketSegmentationTypeB2cCategories;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/market-segmentation-type-b-2-c-categories+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/market-segmentation-type-b-2-c-categories').as('postEntityRequest');
    cy.intercept('DELETE', '/api/market-segmentation-type-b-2-c-categories/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (marketSegmentationTypeB2cCategories) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/market-segmentation-type-b-2-c-categories/${marketSegmentationTypeB2cCategories.id}`,
      }).then(() => {
        marketSegmentationTypeB2cCategories = undefined;
      });
    }
  });

  it('MarketSegmentationTypeB2cCategories menu should load MarketSegmentationTypeB2cCategories page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('market-segmentation-type-b-2-c-categories');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('MarketSegmentationTypeB2cCategories').should('exist');
    cy.url().should('match', marketSegmentationTypeB2cCategoriesPageUrlPattern);
  });

  describe('MarketSegmentationTypeB2cCategories page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(marketSegmentationTypeB2cCategoriesPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create MarketSegmentationTypeB2cCategories page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/market-segmentation-type-b-2-c-categories/new$'));
        cy.getEntityCreateUpdateHeading('MarketSegmentationTypeB2cCategories');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', marketSegmentationTypeB2cCategoriesPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/market-segmentation-type-b-2-c-categories',
          body: marketSegmentationTypeB2cCategoriesSample,
        }).then(({ body }) => {
          marketSegmentationTypeB2cCategories = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/market-segmentation-type-b-2-c-categories+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/market-segmentation-type-b-2-c-categories?page=0&size=20>; rel="last",<http://localhost/api/market-segmentation-type-b-2-c-categories?page=0&size=20>; rel="first"',
              },
              body: [marketSegmentationTypeB2cCategories],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(marketSegmentationTypeB2cCategoriesPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details MarketSegmentationTypeB2cCategories page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('marketSegmentationTypeB2cCategories');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', marketSegmentationTypeB2cCategoriesPageUrlPattern);
      });

      it('edit button click should load edit MarketSegmentationTypeB2cCategories page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('MarketSegmentationTypeB2cCategories');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', marketSegmentationTypeB2cCategoriesPageUrlPattern);
      });

      it('edit button click should load edit MarketSegmentationTypeB2cCategories page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('MarketSegmentationTypeB2cCategories');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', marketSegmentationTypeB2cCategoriesPageUrlPattern);
      });

      it('last delete button click should delete instance of MarketSegmentationTypeB2cCategories', () => {
        cy.intercept('GET', '/api/market-segmentation-type-b-2-c-categories/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('marketSegmentationTypeB2cCategories').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', marketSegmentationTypeB2cCategoriesPageUrlPattern);

        marketSegmentationTypeB2cCategories = undefined;
      });
    });
  });

  describe('new MarketSegmentationTypeB2cCategories page', () => {
    beforeEach(() => {
      cy.visit(`${marketSegmentationTypeB2cCategoriesPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('MarketSegmentationTypeB2cCategories');
    });

    it('should create an instance of MarketSegmentationTypeB2cCategories', () => {
      cy.get(`[data-cy="value"]`).type('XSS').should('have.value', 'XSS');

      cy.get(`[data-cy="description"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="placeholder"]`).type('transmitter European').should('have.value', 'transmitter European');

      cy.get(`[data-cy="uniqueCharacteristic"]`).type('Metrics holistic').should('have.value', 'Metrics holistic');

      cy.get(`[data-cy="language"]`).select('GREEK');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        marketSegmentationTypeB2cCategories = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', marketSegmentationTypeB2cCategoriesPageUrlPattern);
    });
  });
});
