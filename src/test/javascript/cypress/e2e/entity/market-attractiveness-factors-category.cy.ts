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

describe('MarketAttractivenessFactorsCategory e2e test', () => {
  const marketAttractivenessFactorsCategoryPageUrl = '/market-attractiveness-factors-category';
  const marketAttractivenessFactorsCategoryPageUrlPattern = new RegExp('/market-attractiveness-factors-category(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const marketAttractivenessFactorsCategorySample = { value: 'mesh', tab: 4551, language: 'GREEK' };

  let marketAttractivenessFactorsCategory;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/market-attractiveness-factors-categories+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/market-attractiveness-factors-categories').as('postEntityRequest');
    cy.intercept('DELETE', '/api/market-attractiveness-factors-categories/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (marketAttractivenessFactorsCategory) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/market-attractiveness-factors-categories/${marketAttractivenessFactorsCategory.id}`,
      }).then(() => {
        marketAttractivenessFactorsCategory = undefined;
      });
    }
  });

  it('MarketAttractivenessFactorsCategories menu should load MarketAttractivenessFactorsCategories page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('market-attractiveness-factors-category');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('MarketAttractivenessFactorsCategory').should('exist');
    cy.url().should('match', marketAttractivenessFactorsCategoryPageUrlPattern);
  });

  describe('MarketAttractivenessFactorsCategory page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(marketAttractivenessFactorsCategoryPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create MarketAttractivenessFactorsCategory page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/market-attractiveness-factors-category/new$'));
        cy.getEntityCreateUpdateHeading('MarketAttractivenessFactorsCategory');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', marketAttractivenessFactorsCategoryPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/market-attractiveness-factors-categories',
          body: marketAttractivenessFactorsCategorySample,
        }).then(({ body }) => {
          marketAttractivenessFactorsCategory = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/market-attractiveness-factors-categories+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/market-attractiveness-factors-categories?page=0&size=20>; rel="last",<http://localhost/api/market-attractiveness-factors-categories?page=0&size=20>; rel="first"',
              },
              body: [marketAttractivenessFactorsCategory],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(marketAttractivenessFactorsCategoryPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details MarketAttractivenessFactorsCategory page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('marketAttractivenessFactorsCategory');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', marketAttractivenessFactorsCategoryPageUrlPattern);
      });

      it('edit button click should load edit MarketAttractivenessFactorsCategory page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('MarketAttractivenessFactorsCategory');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', marketAttractivenessFactorsCategoryPageUrlPattern);
      });

      it('edit button click should load edit MarketAttractivenessFactorsCategory page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('MarketAttractivenessFactorsCategory');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', marketAttractivenessFactorsCategoryPageUrlPattern);
      });

      it('last delete button click should delete instance of MarketAttractivenessFactorsCategory', () => {
        cy.intercept('GET', '/api/market-attractiveness-factors-categories/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('marketAttractivenessFactorsCategory').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', marketAttractivenessFactorsCategoryPageUrlPattern);

        marketAttractivenessFactorsCategory = undefined;
      });
    });
  });

  describe('new MarketAttractivenessFactorsCategory page', () => {
    beforeEach(() => {
      cy.visit(`${marketAttractivenessFactorsCategoryPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('MarketAttractivenessFactorsCategory');
    });

    it('should create an instance of MarketAttractivenessFactorsCategory', () => {
      cy.get(`[data-cy="value"]`).type('implementation').should('have.value', 'implementation');

      cy.get(`[data-cy="tab"]`).type('7772').should('have.value', '7772');

      cy.get(`[data-cy="description"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="language"]`).select('GREEK');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        marketAttractivenessFactorsCategory = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', marketAttractivenessFactorsCategoryPageUrlPattern);
    });
  });
});
